package PrintingFactoryDriver;

import PrintingFactoryProducts.PageSize;
import PrintingFactoryProducts.PaperType;

import java.util.Objects;

public class Paper {
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
}


