package org.cinemacraftstudios.discord;

public enum PlayerMsgType {
    JOINED, LEFT;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
