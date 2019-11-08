package com.villcore.config.server;

import com.villcore.config.server.service.DelayedOperation;
import com.villcore.config.server.service.DelayedOperationPurgatory;

import java.util.Arrays;
import java.util.Scanner;

/**
 * created by WangTao on 2019-10-28
 */
public class PurgatoryDemo {

    public static void main(String[] args) throws InterruptedException {
        DelayedOperationPurgatory<DelayedOperation> purgatory = new DelayedOperationPurgatory<>("TEST");

        for (int i = 0; i < 300; i++) {
            int finalI = i;
            purgatory.tryCompleteElseWatch(new DelayedOperation(finalI * 10, null) {
                @Override
                public void onExpiration() {
                    //System.out.println("expiration at " + (finalI * 1000));
                    System.out.println(purgatory.watched() + "-" + purgatory.delayed());

                }

                @Override
                public void onComplete() {
                    // System.out.println("=============onComplete");
                    System.out.println("onComplete at " + (finalI * 1000));
                }

                @Override
                public boolean tryComplete() {
                    // System.out.println("tryComplete");
                    return false;
                }
            }, Arrays.asList("test" + i / 10));
        }

        Scanner scanner = new Scanner(System.in);
        String key;
        while ((key = scanner.next()).trim().length() > 0) {
            purgatory.checkAndComplete(key.trim());
        }
    }
}
