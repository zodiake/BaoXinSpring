package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zodiake on 2014/7/11.
 */
@Entity
@Table(name="t_ec_user_preferfabriccategory")
public class FavouriteFabricCategory {
    @Embeddable
    public static class Id implements Serializable {
        @Column(name="user_id")
        private String userId;

        @Column(name="category_id")
        private String categoryId;

        public Id(){}

        public Id(String userId,String categoryId){
            this.userId=userId;
            this.categoryId=categoryId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            if (categoryId != null ? !categoryId.equals(id.categoryId) : id.categoryId != null) return false;
            if (userId != null ? !userId.equals(id.userId) : id.userId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
            return result;
        }
    }

    @EmbeddedId
    private Id id=new Id();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private EcUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private FabricCategory category;

    public FavouriteFabricCategory(){}

    public FavouriteFabricCategory(EcUser user,FabricCategory category){
        this.id.categoryId=category.getId();
        this.id.userId=user.getId();
        this.user=user;
        this.category=category;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public FabricCategory getCategory() {
        return category;
    }

    public void setCategory(FabricCategory category) {
        this.category = category;
    }
}
