package PrintingFactoryDriver;

import PrintingFactoryProducts.PageSize;
import PrintingFactoryProducts.PaperType;

import java.io.Serializable;
import java.util.Objects;

public class Paper implements Comparable<Paper>, Serializable {
    private final PageSize pageSize;
    private final PaperType paperType;

    public Paper(PageSize pageSize, PaperType paperType) {
        this.pageSize = pageSize;
        this.paperType = paperType;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paper paper = (Paper) o;
        return pageSize == paper.pageSize &&
                paperType == paper.paperType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageSize, paperType);
    }

    @Override
    public String toString() {
        return "Paper{" +
                "pageSize=" + pageSize +
                ", paperType=" + paperType +
                '}';
    }

    @Override
    public int compareTo(Paper other) {
        int pageSizeComparison = this.pageSize.compareTo(other.pageSize);
        if (pageSizeComparison != 0) {
            return pageSizeComparison;
        }

        // pageSize are equal, compare paperType
        return this.paperType.compareTo(other.paperType);
    }
}


