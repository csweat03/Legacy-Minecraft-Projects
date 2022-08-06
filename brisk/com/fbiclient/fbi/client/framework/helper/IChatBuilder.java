package com.fbiclient.fbi.client.framework.helper;

import me.xx.utility.chat.ChatBuilder;

public interface IChatBuilder {
	
	default ChatBuilder chatBuilderFactory() {
        return new ChatBuilder();
    }
    
    default ChatBuilder clientChatMessageFactory() {
        return new ChatBuilder().appendPrefix();
    }
    
    default ChatBuilder cb() {
        return chatBuilderFactory();
    }
    
    default ChatBuilder clientChatMsg() {
        return clientChatMessageFactory();
    }
    
}
