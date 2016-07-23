package org.unstoppable.projectstack.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.model.ChannelCreationForm;
import org.unstoppable.projectstack.service.ChannelService;

public class ChannelValidator implements Validator {
    private final ChannelService channelService;
    private final String communityTitle;

    public ChannelValidator(ChannelService channelService, String communityTitle) {
        this.channelService = channelService;
        this.communityTitle = communityTitle;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Channel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChannelCreationForm channelForm = (ChannelCreationForm) target;
        titleValidation(errors, channelForm);
    }

    private void titleValidation(Errors errors, ChannelCreationForm channelForm) {
        // Only latin chars, numbers and "_"
        if (!channelForm.getTitle().matches("\\w+")) {
            errors.rejectValue("title", "channel.error.title.chars");
        }
        // Check title existence
        if (!channelService.checkTitle(channelForm.getTitle(), communityTitle)) {
            errors.reject("title", "channel.error.title_exist");
        }
    }
}
