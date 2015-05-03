/*
 * Copyright (C) 2007 Esmertec AG.
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sec.administrator.testmms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Objects;

public class MessageStatusReceiver extends BroadcastReceiver {
    public static final String MESSAGE_STATUS_RECEIVED_ACTION =
            "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MESSAGE_STATUS_RECEIVED_ACTION.equals(intent.getAction())) {
//            intent.setClass(context, MessageStatusService.class);
//            context.startService(intent);
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg = "";
           if (bundle != null) {
               Object[] pdus = (Object[]) bundle.get("pdus");
               msgs = new SmsMessage[pdus.length];
               for (int i=0; i<msgs.length;i++) {
                   msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                   msg = msgs[i].getMessageBody().toString();
               }
               Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
               toast.show();
               this.abortBroadcast();
               Intent msgIntent = new Intent();
               msgIntent.setAction("SMS_READY");
               msgIntent.putExtra("sms",msg);
               context.sendBroadcast(msgIntent);

           }
       }
    }
}
