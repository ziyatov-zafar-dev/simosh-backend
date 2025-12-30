package com.codebyz.simoshbackend.about;

import com.codebyz.simoshbackend.about.dto.AboutRequest;
import com.codebyz.simoshbackend.about.dto.AboutResponse;
import com.codebyz.simoshbackend.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AboutService {

    private static final long SINGLETON_ID = 1L;

    private final AboutRepository aboutRepository;

    public AboutService(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    public AboutResponse get() {
        About about = aboutRepository.findById(SINGLETON_ID).orElseGet(() -> {
            About created = new About();
            created.setId(SINGLETON_ID);
            created.setDescriptionUz("");
            created.setDescriptionRu("");
            created.setDescriptionTr("");
            created.setDescriptionEn("");
            created.setOfficeAddressUz("");
            created.setOfficeAddressRu("");
            created.setOfficeAddressTr("");
            created.setOfficeAddressEn("");
            created.setInstagram("");
            created.setTelegram("");
            created.setPhone("");
            return aboutRepository.save(created);
        });
        return toResponse(about);
    }

    public AboutResponse update(AboutRequest request) {
        About about = aboutRepository.findById(SINGLETON_ID).orElseGet(() -> {
            About created = new About();
            created.setId(SINGLETON_ID);
            return created;
        });

        boolean changed = false;
        if (StringUtils.hasText(request.getDescriptionUz())) {
            about.setDescriptionUz(request.getDescriptionUz());
            changed = true;
        }
        if (StringUtils.hasText(request.getDescriptionRu())) {
            about.setDescriptionRu(request.getDescriptionRu());
            changed = true;
        }
        if (StringUtils.hasText(request.getDescriptionTr())) {
            about.setDescriptionTr(request.getDescriptionTr());
            changed = true;
        }
        if (StringUtils.hasText(request.getDescriptionEn())) {
            about.setDescriptionEn(request.getDescriptionEn());
            changed = true;
        }
        if (StringUtils.hasText(request.getOfficeAddressUz())) {
            about.setOfficeAddressUz(request.getOfficeAddressUz());
            changed = true;
        }
        if (StringUtils.hasText(request.getOfficeAddressRu())) {
            about.setOfficeAddressRu(request.getOfficeAddressRu());
            changed = true;
        }
        if (StringUtils.hasText(request.getOfficeAddressTr())) {
            about.setOfficeAddressTr(request.getOfficeAddressTr());
            changed = true;
        }
        if (StringUtils.hasText(request.getOfficeAddressEn())) {
            about.setOfficeAddressEn(request.getOfficeAddressEn());
            changed = true;
        }
        if (StringUtils.hasText(request.getInstagram())) {
            about.setInstagram(request.getInstagram());
            changed = true;
        }
        if (StringUtils.hasText(request.getTelegram())) {
            about.setTelegram(request.getTelegram());
            changed = true;
        }
        if (StringUtils.hasText(request.getPhone())) {
            about.setPhone(request.getPhone());
            changed = true;
        }

        if (!changed) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Yangilash uchun kamida bitta matn yuboring");
        }

        About saved = aboutRepository.save(about);
        return toResponse(saved);
    }

    private AboutResponse toResponse(About about) {
        return new AboutResponse(
                about.getDescriptionUz(),
                about.getDescriptionRu(),
                about.getDescriptionTr(),
                about.getDescriptionEn(),
                about.getOfficeAddressUz(),
                about.getOfficeAddressRu(),
                about.getOfficeAddressTr(),
                about.getOfficeAddressEn(),
                about.getInstagram(),
                about.getTelegram(),
                about.getPhone()
        );
    }
}
