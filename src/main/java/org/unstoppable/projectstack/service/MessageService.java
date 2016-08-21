package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unstoppable.projectstack.dao.MessageDAO;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Message;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public void add(Message message) {
        messageDAO.add(message);
    }

    public void delete(Message message) {
        messageDAO.add(message);
    }

    public void update(Message message) {
        messageDAO.update(message);
    }

    public List<Message> getByChannel(Channel channel) {
        return messageDAO.getByChannel(channel);
    }

    public List<Message> getByChannelWithLimitation(Channel channel, int startRowPosition, int maxResult) {
        return messageDAO.getByChannelWithLimitation(channel, startRowPosition, maxResult);
    }
}
