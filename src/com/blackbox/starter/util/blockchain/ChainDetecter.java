package com.blackbox.starter.util.blockchain;

import com.blackbox.starter.events.CarEvent;
import com.blackbox.starter.models.EventBlock;
import com.blackbox.starter.util.EncryptionUtil;
import com.blackbox.starter.util.Hasher;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.*;

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

    public List<EventBlock> getListFromFile(int startPosition, int endPosition){
        List<EventBlock> resultList = new ArrayList<>();
        int start, end;
        if (startPosition < 0 || endPosition < 0 || endPosition < startPosition) {
           File folder = new File("history_storage\\");
            String [] fileList =  folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("history_");
                }
            });
            for (String fileName : fileList) {
                resultList.addAll(getListFromFile("history_storage\\"+fileName));
            }

        } else {
            start = (int) Math.floor((double) startPosition / 100.0) * 100;
            end = (int) Math.ceil((double) startPosition / 100.0) * 100;
            for (int i = start; i < end; i+=100) {
                resultList.addAll(getListFromFile("history_storage\\history_" + start + "_" + (start+99) + ".out"));
            }
        }
        return resultList;

    }

    private List<EventBlock> getListFromFile(String fileName) {
        List<EventBlock> resultList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream oin = new ObjectInputStream(fis);
            resultList.addAll((ArrayList<EventBlock>) oin.readObject());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return resultList;

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
        if (classList.isEmpty()) return eventList;
        List<CarEvent> result = new ArrayList<>();
        for (CarEvent event : eventList) {
            if (classList.contains(event.getClass())) {
                result.add(event);
            }
        }
        return result;
    }

}
