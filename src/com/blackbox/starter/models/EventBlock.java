package com.blackbox.starter.models;

import com.blackbox.starter.events.CarEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

//import static org.spongycastle.crypto.tls.CipherType.block;

/**
 * Created by toktar.
 */
public class EventBlock implements Serializable {

    private byte[] parentHash;
    private List<CarEvent> event;
    private byte[] eventHash;
    private long timestamp;
    private byte[] signature;
    private long nonce =0;

    public byte[] getEventHash() {
        return eventHash;
    }

    public void setEventHash(byte[] eventHash) {
        this.eventHash = eventHash;
    }



    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }



    public byte[] getParentHash() {
        return parentHash;
    }

    public void setParentHash(byte[] parentHash) {
        this.parentHash = parentHash;
    }

    public List<CarEvent> getEvent() {
        return event;
    }

    public void setEvent(List<CarEvent> event) {
        this.event = event;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public byte[] getHeader() throws IOException {
        ByteArrayOutputStream blockInByteArray = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(blockInByteArray);
        oos.writeObject(event);
        return blockInByteArray.toByteArray();
    }
}
