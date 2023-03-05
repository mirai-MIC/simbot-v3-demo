package org.simbot.sessionbuild;

import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.ID;
import love.forte.simbot.event.ContinuousSessionContext;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @BelongsProject: simbot-v3-demo
 * @BelongsPackage: org.simbot.sessionbuild
 * @Author: MIC
 * @CreateTime: 2023-02-28  19:58
 * @Description:
 * @Version: 1.0
 */

@Component
@Slf4j
public class sessionContextBuild {


    @Listener
    @Filter(value = "/测试")
    public void test(GroupMessageEvent event,ContinuousSessionContext sessionContext){
        String qqId = event.getAuthor().getId().toString();
        if (sessionContext.getProvider(qqId)!=null){
            return;
        }
        event.getSource().sendBlocking("text");
        String waiting = waiting(20, event, GroupMessageEvent.Key, TimeUnit.SECONDS, sessionContext);
        event.getSource().sendBlocking(waiting);
    }






    /**
     *
     * @param key
     * @param seconds
     * @param continuousSessionContext
     * @return
     */
    public static String waiting(int Time,GroupMessageEvent event, GroupMessageEvent.Key key, TimeUnit seconds, ContinuousSessionContext continuousSessionContext) {
        String qqId = event.getAuthor().getId().toString();
        ID id = event.getAuthor().getId();
        ID groupId = event.getGroup().getId();
        continuousSessionContext.waitingForNextMessage(qqId, key, Time, seconds, (e, c) -> {

            if (!(c.getAuthor().getId().equals(id) && c.getGroup().getId().equals(groupId))) {
                return false;
            }

            for (Message.Element<?> message : c.getMessageContent().getMessages()) {
                if (message instanceof Image<?>) {
                    return true;
                }
            }
            return false;
        });

        Messages messages = event.getMessageContent().getMessages();

        //        for (Message.Element<?> message : messages)
//            if (message instanceof Image<?> image) return image.getResource().getName();
        return event.getMessageContent().getMessageId().toString();
    }

}
