package com.baosight.scc.ec.service.redis;

import com.baosight.scc.ec.model.CartLine;
import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.service.CartService;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.utils.RedisConstant.ConstantKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.baosight.scc.ec.utils.RedisConstant.ConstantKey.*;

/**
 * Created by Administrator on 2014/10/15.
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private ItemService itemService;
    @Override
    @Transactional(readOnly = true)
    public List<CartLine> findAllItems(String id, Integer offset) {
        Set<String> items = template.opsForZSet().range(CART + id, 0, -1);
        List<CartLine> result = new LinkedList<CartLine>();
        double price = 0.0;
        String cartId = CART + id;
        for (String s : items) {
            String itemId =   s;

            int quantity = template.opsForZSet().score(cartId, s).intValue();
            CartLine c = constructItemFromRedis(itemId, quantity);

            Map<Double, Double> priceRanges = c.getPriceRange();
            List<Double> ranges = new ArrayList<Double>();

            for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
                ranges.add(entry.getKey());
            }
            Collections.sort(ranges);
            for (int i = 0; i < ranges.size(); i++) {
                if (quantity < ranges.get(0)) {
                    price = -1;
                    break;
                } else if(quantity >= ranges.get(ranges.size()-1)) {
                    price = priceRanges.get(ranges.get(ranges.size()-1));
                    break;
                } else if (quantity <= ranges.get(i)-1) {
                    price = priceRanges.get(ranges.get(i-1));
                    break;
                }
            }
            c.setPrice(price);
            c.setSummary(multiply(price,c.getQuantity()));
            result.add(c);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public CartLine findCartById(String userId, String itemId) {
        double price = 0.0;
        String cartId = CART + userId;
        int quantity = template.opsForZSet().score(cartId, itemId).intValue();
        CartLine c = constructItemFromRedis(itemId, quantity);

        Map<Double, Double> priceRanges = c.getPriceRange();
        List<Double> ranges = new ArrayList<Double>();

        for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
            ranges.add(entry.getKey());
        }
        Collections.sort(ranges);
        for (int i = 0; i < ranges.size(); i++) {
            if (quantity < ranges.get(0)) {
                price = -1;
                break;
            } else if(quantity >= ranges.get(ranges.size()-1)) {
                price = priceRanges.get(ranges.get(ranges.size()-1));
                break;
            } else if (quantity <= ranges.get(i)-1) {
                price = priceRanges.get(ranges.get(i-1));
                break;
            }
        }
        c.setPrice(price);
        c.setSummary(multiply(price,c.getQuantity()));
        return c;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addItem(Item item, String cartId, int num) {
        template.opsForZSet().add(CART + cartId, item.getId(), num);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void increaseItemCount(Item item, String cartId, int num) {
        template.opsForZSet().incrementScore(CART + cartId, item.getId(), num);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existItem(String key, String cartId) {
        Long rank = template.opsForZSet().rank(CART + cartId, key);
        if (rank == null)
            return false;
        return true;
    }

    @Override
    public void deleteItem(String id, String itemId) {
        template.opsForZSet().remove(CART+id,itemId);
    }

    @Override
    public void deleteAll(String id) {
        template.opsForZSet().removeRange(CART+id,0,-1);
    }

    private CartLine constructItemFromRedis(String id, int quantity) {
        String itemId=REDIS_ITEM+id;
        CartLine cartLine = new CartLine();
        Item item = itemService.findByIdThroughRedis(id);
        String name = (String) template.opsForHash().get(itemId, ItemKey.name);
        String providerId = (String) template.opsForHash().get(itemId, ItemKey.providerId);
        String providerName = (String) template.opsForHash().get(itemId, ItemKey.providerName);
        String cover = (String) template.opsForHash().get(itemId, ItemKey.cover);
        String state = (String) template.opsForHash().get(itemId, ItemKey.state);
        String url = (String) template.opsForHash().get(itemId, ItemKey.url);
        cartLine.setItemId(id);
        cartLine.setImgPath(cover);
        cartLine.setSupplierId(providerId);
        cartLine.setSupplierName(providerName);
        cartLine.setTitle(name);
        cartLine.setState(ItemState.valueOf(state));
        cartLine.setQuantity(quantity);
        cartLine.setPriceRange(getCartLinePrice(id));
        cartLine.setItemType(url);
        cartLine.setItem(item);
        return cartLine;
    }

    private Map<Double, Double> getCartLinePrice(String id) {
        String priceId = ConstantKey.REDIS_PRICE + id;
        Map<Double, Double> prices = new HashMap<Double, Double>();
        Set<String> keys = (Set) template.opsForHash().keys(priceId);
        for (String key : keys) {
            String value = (String) template.opsForHash().get(priceId, key);
            prices.put(Double.parseDouble(key), Double.parseDouble(value));
        }
        return prices;
    }

    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    @Override
    public Map<String, List<CartLine>> tidyCart(String userId) {
        List<CartLine> cartLines = findAllItems(userId, 0);
        Map<String, List<CartLine>> cartLineMap = new HashMap<String, List<CartLine>>();
        if (cartLines != null){
            for (int i = 0; i < cartLines.size(); i++) {
                CartLine c = cartLines.get(i);
                if(userId.equals(c.getSupplierId().trim())){
                    deleteItem(userId,c.getItemId());
                    continue;
                }else{
                    Map<Double, Double> priceRanges = c.getPriceRange();
                    int quantity = c.getQuantity();
                    double price = 0.0;
                    List<Double> ranges = new ArrayList<Double>();
                    for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
                        ranges.add(entry.getKey());
                    }
                    Collections.sort(ranges);
                    for (int j = 0; j < ranges.size(); j++) {
                        if (quantity < ranges.get(0)) {
                            price = -1;
                            break;
                        } else if(quantity >= ranges.get(ranges.size()-1)) {
                            price = priceRanges.get(ranges.get(ranges.size()-1));
                            break;
                        } else if (quantity <= ranges.get(j)-1) {
                            price = priceRanges.get(ranges.get(j-1));
                            break;
                        }
                    }
                    c.setPrice(price);
                    c.setSummary(multiply(price,quantity));
                    List<CartLine> cartLineList = cartLineMap.get(c.getSupplierId().trim());
                    if(cartLineList == null){
                        cartLineList = new ArrayList<CartLine>();
                        cartLineList.add(c);
                        cartLineMap.put(c.getSupplierId().trim(),cartLineList);
                    }else {
                        cartLineList.add(c);
                        cartLineMap.put(c.getSupplierId().trim(),cartLineList);
                    }
                }
            }
        }
        return cartLineMap;
    }

    @Override
    @Transactional(readOnly = true)
    public CartLine findTemplateCart(String itemId, int quantity) {
        double price = 0.0;
        CartLine c = constructItemFromRedis(itemId, quantity);

        Map<Double, Double> priceRanges = c.getPriceRange();
        List<Double> ranges = new ArrayList<Double>();

        for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
            ranges.add(entry.getKey());
        }
        Collections.sort(ranges);
        for (int i = 0; i < ranges.size(); i++) {
            if (quantity < ranges.get(0)) {
                price = -1;
                break;
            } else if(quantity >= ranges.get(ranges.size()-1)) {
                price = priceRanges.get(ranges.get(ranges.size()-1));
                break;
            } else if (quantity <= ranges.get(i)-1) {
                price = priceRanges.get(ranges.get(i-1));
                break;
            }
        }
        c.setPrice(price);
        c.setSummary(multiply(price,c.getQuantity()));
        return c;
    }
}
