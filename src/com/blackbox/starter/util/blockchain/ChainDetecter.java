package com.blackbox.starter.util.blockchain;

import com.blackbox.starter.events.CarEvent;
import com.blackbox.starter.models.EventBlock;
import com.blackbox.starter.util.EncryptionUtil;
import com.blackbox.starter.util.Hasher;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.*;

import static com.blackbox.starter.util.EncryptionUtil.PRIVATE_KEY_FILE;

/**
 * Created by Kida on 28.03.2017.
 */
public class ChainDetecter {


    public boolean isChainCorrect(List<EventBlock> eventList) throws GeneralSecurityException, IOException, ClassNotFoundException {
        if (!isBlockCorrect(eventList.get(0))) return false;
        for (int i = 1; i < eventList.size(); i++) {
            if (!Arrays.equals(eventList.get(i - 1).getEventHash(), eventList.get(i).getParentHash())
                    || !isBlockCorrect(eventList.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isBlockCorrect(EventBlock block) throws GeneralSecurityException, IOException, ClassNotFoundException {
        Hasher hasher = new Hasher();
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("keys\\private.key"));
        final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
        int hashLength = block.getEventHash().length;
        boolean res = Arrays.equals(block.getEventHash(), hasher.hash(block.getHeader(), block.getNonce()))
                && block.getEventHash()[hashLength - 1] == 0
                && block.getEventHash()[hashLength - 2] == 0;

        ByteArrayOutputStream blockInByteArray = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(blockInByteArray);
        oos.writeObject(block.getEvent());

        res = res && Arrays.equals(blockInByteArray.toByteArray(), EncryptionUtil.decrypt(block.getSignature(), privateKey));
        return res;
    }

    public List<EventBlock> getListFromFile(int startPosition, int endPosition) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("history_storage\\history_" + startPosition
                + "_" + endPosition + ".out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        return (ArrayList<EventBlock>) oin.readObject();

    }

    public List<CarEvent> getEventList(List<EventBlock> blockList) {
        List<CarEvent> eventList = new ArrayList<>();
        for (EventBlock block : blockList) {
            eventList.addAll(block.getEvent());
        }
        Collections.sort(eventList);
        return eventList;
    }

    public List<CarEvent> getEventWithType(List<CarEvent> eventList, Set<Class> classList) {
        List<CarEvent> result = new ArrayList<>();
        for (CarEvent event : eventList) {
            if (classList.contains(event.getClass())) {
                result.add(event);
            }
        }
        return result;
    }

}
