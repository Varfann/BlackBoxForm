package com.blackbox.starter.util.blockchain;

import com.blackbox.starter.events.CarEvent;
import com.blackbox.starter.models.EventBlock;
import com.blackbox.starter.util.EncryptionUtil;
import com.blackbox.starter.util.Hasher;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import static com.blackbox.starter.util.EncryptionUtil.PUBLIC_KEY_FILE;

/**
 * Created by Kida on 19.03.2017.
 */
public class Miner extends Thread {


    private List<EventBlock> blockList;
    public List<CarEvent> pendingList;
    public List<CarEvent> processingList;


    private byte[] target;
    // BlockController blockController;

    public Miner() {
        System.out.println("Miner controller starting");
        // this.blockController = blockController;
        EncryptionUtil.generateKey();
        blockList = new ArrayList<>();
        pendingList = new ArrayList<>();
        target = new byte[32];
        for (int i = 0; i < 32; i++) {
            target[i] = (byte) (i >= 30 ? 0 : 0xFF); //TODO param
        }
        System.out.println("Miner controller started");

    }


    /**
     * add Event pendingList - list of pending event, save pending event in file
     * For listener
     *
     * @param newEvent
     * @throws IOException
     */
     synchronized public void addEvent(CarEvent newEvent) throws IOException {
        System.out.println("add new event");
        pendingList.add(newEvent);
        savePendingEventsToFile();
    }

    synchronized public void movePendingEvent(){
        processingList = new ArrayList<>(pendingList);
    }


    /**
     * Start of Mainer
     * Detect new event in pendingList and generateNonceAndSaveBlock
     * TODO: Stand lists from files (backup)
     */
    @Override
     public void run() {
        System.out.println("Miner run");

        movePendingEvent();
        int countOfWaitingEvent = processingList.size();
        while (true) {
            movePendingEvent();
            //Lock lock = new ReentrantLock();
            //lock.lock();
            try {
                if (processingList.isEmpty() || countOfWaitingEvent != processingList.size()) {
                    countOfWaitingEvent = processingList.size();
                    continue;
                }
            } finally {
                //  lock.unlock();
            }
            System.out.println("Detect new element");

            try {
                generateNonceAndSaveBlock(countOfWaitingEvent);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * If count of current pending event is changing then function will stop.
     *
     * @param curPendingEvent
     */
     private void generateNonceAndSaveBlock(int curPendingEvent) throws GeneralSecurityException, IOException, ClassNotFoundException {
        boolean nonceNotFound = true;
        int nonce = 0;
        Hasher hasher = null;

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        final PublicKey publicKey = (PublicKey) inputStream.readObject();

        EventBlock eventBlock = new EventBlock();
        eventBlock.setEvent(new ArrayList<>(pendingList));
        eventBlock.setParentHash(blockList.isEmpty() ? null : blockList.get(blockList.size() - 1).getEventHash());
        eventBlock.setTimestamp(System.currentTimeMillis());
        eventBlock.setSignature(EncryptionUtil.encrypt(eventBlock.getEvent(), publicKey));
        //TODO - fill Block
        try {
            hasher = new Hasher();
            byte[] header = eventBlock.getHeader();

            while (nonce < Integer.MAX_VALUE) {
                if (curPendingEvent != pendingList.size() && pendingList.size()<3) return;
                if (tryNonce(nonce, hasher, header)) break;
                nonce++;
            }

        } catch (GeneralSecurityException e) {
            throw new RuntimeException("hasher error"); //TODO
        }

        if (nonce == Integer.MAX_VALUE) {
            throw new RuntimeException("nonce doesn't exists"); //TODO
        } else {
            eventBlock.setNonce(nonce);         // if a problem don't detect, save block
            eventBlock.setEventHash(hasher.hash(eventBlock.getHeader(), nonce));
            try {
                saveNewBlock(eventBlock);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Save error: " + e.getMessage());
            }
        }
    }

    /**
     * save block with nonce in list and file, delete events by block from pending list.
     * Save pending list
     *
     * @param eventBlock
     * @throws IOException
     */
     synchronized private void saveNewBlock(EventBlock eventBlock) throws IOException {
        blockList.add(eventBlock);

        //pendingList.remove(eventBlock.getEvent());
        for (int i = 0; i < eventBlock.getEvent().size(); i++) {
            pendingList.remove(eventBlock.getEvent().get(i));
        }
        saveBlockListToFile((blockList.size() / 100) * 100, (blockList.size() / 100 + 1) * 100 - 1);
        savePendingEventsToFile();
    }


    private boolean tryNonce(int nonce, Hasher hasher, byte[] header) throws GeneralSecurityException {
        byte[] hash = hasher.hash(header, nonce);
        for (int i = hash.length - 1; i >= 0; i--) {                //?!
            if ((hash[i] & 0xff) > (target[i] & 0xff))
                return false;
            if ((hash[i] & 0xff) < (target[i] & 0xff))
             return true;
        }
        return true;
    }

    private void saveBlockListToFile(int startPosition, int endPosition) throws IOException {
        List<EventBlock> curList = new ArrayList<>();
        for (int i = startPosition; i <= endPosition && i < blockList.size(); i++) {
            curList.add(blockList.get(i));
        }

        FileOutputStream fos = new FileOutputStream("history_storage\\history_" + startPosition
                + "_" + endPosition + ".out"); //TODO write file every day
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(curList);
        oos.flush();
        oos.close();

    }

     private void savePendingEventsToFile() throws IOException {
        FileOutputStream fos = new FileOutputStream("tmp\\pending_event.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(pendingList);
        oos.flush();
        oos.close();
    }

    public void getListFromFile(int startPosition, int endPosition) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("history_storage\\history_" + startPosition
                + "_" + endPosition + ".out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        List<EventBlock> curList = (ArrayList<EventBlock>) oin.readObject();
        System.out.println(curList);

    }

}
