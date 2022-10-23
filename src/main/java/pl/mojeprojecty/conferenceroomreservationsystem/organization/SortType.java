package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.springframework.data.domain.Sort;

public enum SortType {
    ASC, DESC;

    public Sort getSort(String fieldName) {
        Sort sort;
        if (this == SortType.ASC) {
            sort = Sort.by(Sort.Direction.ASC, fieldName);
        } else {
            sort = Sort.by(Sort.Direction.DESC, fieldName);
        }
        return sort;
    }
}
