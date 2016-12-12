package org.unstoppable.montao.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.model.ChannelCreationForm;
import org.unstoppable.montao.service.ChannelService;

public class ChannelValidator implements Validator {
    private final ChannelService channelService;

    public ChannelValidator(ChannelService channelService) {
        this.channelService = channelService;
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
        if (!channelService.checkTitle(channelForm.getTitle(), channelForm.getCommunityTitle())) {
            errors.reject("title", "channel.error.title_exist");
        }
    }
}
