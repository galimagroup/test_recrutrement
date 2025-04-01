package sow.issa.recrutement.mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntityMapper<REQ, RESP, ENT> {

    ENT asEntity(REQ request);
    RESP asResponse(ENT entity);

    List<RESP> parse(List<ENT> entities);

    List<ENT> parseToEntity(List<REQ> entities);


    default Page<RESP> asPage(Page<ENT> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<RESP> dtoList = parse(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    default  Page<RESP> asPage(List<ENT> entities, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<RESP> dtoList = parse(entities);
        return new PageImpl<>(dtoList, pageable, entities.size());
    }
}
