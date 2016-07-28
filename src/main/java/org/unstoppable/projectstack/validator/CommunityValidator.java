package org.unstoppable.projectstack.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.model.CommunityCreationForm;
import org.unstoppable.projectstack.service.CommunityService;

public class CommunityValidator implements Validator {
    private final CommunityService communityService;

    public CommunityValidator(CommunityService communityService) {
        if (communityService == null) {
            throw new IllegalArgumentException("The supplied [Service] is required and must not be null.");
        }
        this.communityService = communityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Community.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CommunityCreationForm communityForm = (CommunityCreationForm) target;
        titleValidation(errors, communityForm);
    }

    /**
     * This method is used to validation title field.
     *
     * @param errors        Errors instance.
     * @param communityForm New community.
     */
    private void titleValidation(Errors errors, CommunityCreationForm communityForm) {
        // Only latin chars, numbers and "_"
        if (!communityForm.getTitle().matches("\\w+")) {
            errors.rejectValue("title", "community.error.title.chars");
        }
        // Check title existence
        if (!communityService.checkTitle(communityForm.getTitle())) {
            errors.reject("title", "community.error.title_exist");
        }
    }
}
