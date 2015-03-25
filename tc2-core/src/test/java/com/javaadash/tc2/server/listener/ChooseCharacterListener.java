package com.javaadash.tc2.server.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.javaadash.tc2.core.TC2AsynchronousGameManager;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.message.ChooseCharacterMessage;
import com.javaadash.tc2.server.TC2Lobby;

public class ChooseCharacterListener implements DataListener<ChooseCharacterMessage> {

	 private TC2Lobby lobby;
	 private TC2AsynchronousGameManager gameManager = new TC2AsynchronousGameManager();
	 
	 public ChooseCharacterListener(TC2Lobby lobby) {
		 this.lobby = lobby;
	 }
	 
	@Override
	public void onData(SocketIOClient client, ChooseCharacterMessage msg,
			AckRequest ack) throws Exception {
		
		GameContext currentContext = lobby.getCurrentGames().get(client.get("roomId"));
		
		System.out.println("currentContext =" +currentContext);
	}

}
