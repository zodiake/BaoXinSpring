package com.baosight.scc.ec.tags;

import org.springframework.web.servlet.tags.form.AbstractHtmlElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;

/**
 * Created by Administrator on 2014/9/29.
 */
public class PageUtil extends AbstractHtmlElementTag {
    private int current;
    private int total;
    private String href;
    private boolean dataPage;
    private String dataUrl;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public boolean isDataPage() {
        return dataPage;
    }

    public void setDataPage(boolean dataPage) {
        this.dataPage = dataPage;
    }

    public String getFirstPage() {
        return getHref() == null ? null : getHref() + "&page=1";
    }

    public String getLastPage() {
        return getHref() == null ? null : getHref() + "&page=" + total;
    }

    public String getCurrentHref(int i) {
        return getHref() == null ? null : getHref() + "&page=" + i;
    }

    public String getNextPage() {
        if (getCurrent() == getTotal())
            return getHref() == null ? null : getHref() + "&page=" + getTotal();
        else
            return getHref() == null ? null : getHref() + "&page=" + (getCurrent() + 1);
    }

    public String getPrevPage() {
        if (getCurrent() == 1)
            return getHref() == null ? null : getHref() + "&page=" + 1;
        else
            return getHref() == null ? null : getHref() + "&page=" + (getCurrent() - 1);
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        tagWriter.startTag("ul");
        tagWriter.writeAttribute("class", "pagination page");

        if (getTotal() <= 7) {
            writeFirstTag(tagWriter, "<", getPrevPage(), false);
            writeLiTag(0, getTotal(), tagWriter);
        } else {
            if (current <= 4) {
                int end = total >= 7 ? 7 : total;
                writeFirstTag(tagWriter, "<", getPrevPage(), false);
                writeLiTag(0, end, tagWriter);
            } else if (current >= getTotal() - 3 && current <= getTotal()) {
                int begin = getTotal() - 7;
                int end = getTotal();
                writeFirstTag(tagWriter, "<", getPrevPage(), true);
                writeLiTag(begin, end, tagWriter);
            } else {
                int begin = current - 4;
                int end = current + 3 >= total ? total : current + 3;
                writeFirstTag(tagWriter, "<", getPrevPage(), true);
                writeLiTag(begin, end, tagWriter);
            }
        }
        writeLastTag(tagWriter);
        tagWriter.endTag();
        return SKIP_BODY;
    }

    private void writeFirstTag(TagWriter tagWriter, String text, String href, boolean flag) throws JspException {
        tagWriter.startTag("li");
        tagWriter.startTag("a");
        tagWriter.writeOptionalAttributeValue("href", href);
        if (isDataPage()) {
            tagWriter.writeOptionalAttributeValue("data-page", getCurrent() + "");
        }
        tagWriter.writeOptionalAttributeValue("data-url", getDataUrl());
        tagWriter.appendValue(text);
        tagWriter.endTag();
        if (flag) {
            tagWriter.startTag("li");
            tagWriter.startTag("a");
            tagWriter.writeOptionalAttributeValue("href", getFirstPage());
            if (isDataPage())
                tagWriter.writeAttribute("data-page", "1");
            tagWriter.writeOptionalAttributeValue("data-url", getDataUrl());
            tagWriter.appendValue("1");
            tagWriter.endTag();
            tagWriter.endTag();

            tagWriter.startTag("li");
            tagWriter.startTag("a");
            tagWriter.appendValue("…");
            tagWriter.endTag();
            tagWriter.endTag();
        }
        tagWriter.endTag();
    }

    private void writeLastTag(TagWriter tagWriter) throws JspException {
        if (getTotal() > 7 && getCurrent() + 3 < getTotal()) {
            tagWriter.startTag("li");
            tagWriter.startTag("a");
            tagWriter.appendValue("…");
            tagWriter.endTag();
            tagWriter.endTag();

            tagWriter.startTag("li");
            tagWriter.startTag("a");
            tagWriter.writeOptionalAttributeValue("href", getLastPage());
            if (isDataPage())
                tagWriter.writeAttribute("data-page", Integer.toString(getTotal()));
            tagWriter.writeOptionalAttributeValue("data-url", getDataUrl());
            tagWriter.appendValue(Integer.toString(getTotal()));
            tagWriter.endTag();
            tagWriter.endTag();
        }
        tagWriter.startTag("li");
        tagWriter.startTag("a");
        if (isDataPage())
            tagWriter.writeAttribute("data-page", getCurrent() + "");
        else
            tagWriter.writeAttribute("href", getNextPage());

        tagWriter.appendValue(">");
        tagWriter.endTag();
        tagWriter.endTag();
    }

    private void writeLiTag(int begin, int end, TagWriter tagWriter) throws JspException {
        for (int i = begin; i < end; i++) {
            tagWriter.startTag("li");
            if (i + 1 == current)
                tagWriter.writeAttribute("class", "active");
            tagWriter.startTag("a");

            tagWriter.writeOptionalAttributeValue("href", getCurrentHref(i + 1));
            if (isDataPage())
                tagWriter.writeAttribute("data-page", Integer.toString(i + 1));
            writeOptionTag(tagWriter);

            tagWriter.appendValue(Integer.toString(i + 1));
            tagWriter.endTag();
            tagWriter.endTag();
        }
    }

    private void writeOptionTag(TagWriter tagWriter) throws JspException {
        tagWriter.writeOptionalAttributeValue("data-url", getDataUrl());
    }
}
