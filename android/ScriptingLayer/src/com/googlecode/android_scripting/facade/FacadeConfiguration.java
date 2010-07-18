/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.googlecode.android_scripting.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


import com.googlecode.android_scripting.Log;
import com.googlecode.android_scripting.facade.AlarmManagerFacade;
import com.googlecode.android_scripting.facade.AndroidFacade;
import com.googlecode.android_scripting.facade.ApplicationManagerFacade;
import com.googlecode.android_scripting.facade.BluetoothFacade;
import com.googlecode.android_scripting.facade.CameraFacade;
import com.googlecode.android_scripting.facade.CommonIntentsFacade;
import com.googlecode.android_scripting.facade.ConditionManagerFacade;
import com.googlecode.android_scripting.facade.ContactsFacade;
import com.googlecode.android_scripting.facade.EventFacade;
import com.googlecode.android_scripting.facade.EyesFreeFacade;
import com.googlecode.android_scripting.facade.LocationFacade;
import com.googlecode.android_scripting.facade.PhoneFacade;
import com.googlecode.android_scripting.facade.PulseGeneratorFacade;
import com.googlecode.android_scripting.facade.MediaRecorderFacade;
import com.googlecode.android_scripting.facade.SensorManagerFacade;
import com.googlecode.android_scripting.facade.SettingsFacade;
import com.googlecode.android_scripting.facade.SignalStrengthFacade;
import com.googlecode.android_scripting.facade.SmsFacade;
import com.googlecode.android_scripting.facade.SpeechRecognitionFacade;
import com.googlecode.android_scripting.facade.TextToSpeechFacade;
import com.googlecode.android_scripting.facade.ToneGeneratorFacade;
import com.googlecode.android_scripting.facade.WakeLockFacade;
import com.googlecode.android_scripting.facade.WifiFacade;
import com.googlecode.android_scripting.facade.ui.UiFacade;
import com.googlecode.android_scripting.jsonrpc.RpcReceiver;
import com.googlecode.android_scripting.rpc.MethodDescriptor;

/**
 * Encapsulates the list of supported facades and their construction.
 * 
 * @author Damon Kohler (damonkohler@gmail.com)
 * @author Igor Karp (igor.v.karp@gmail.com)
 */
public class FacadeConfiguration {

  private final static Set<Class<? extends RpcReceiver>> sFacadeClassList;
  private final static SortedMap<String, MethodDescriptor> sRpcs =
      new TreeMap<String, MethodDescriptor>();

  static {
    int sdkVersion = 0;
    try {
      sdkVersion = Integer.parseInt(android.os.Build.VERSION.SDK);
    } catch (NumberFormatException e) {
      Log.e(e);
    }

    sFacadeClassList = new HashSet<Class<? extends RpcReceiver>>();
    sFacadeClassList.add(AlarmManagerFacade.class);
    sFacadeClassList.add(AndroidFacade.class);
    sFacadeClassList.add(ApplicationManagerFacade.class);
    sFacadeClassList.add(CameraFacade.class);
    sFacadeClassList.add(CommonIntentsFacade.class);
    sFacadeClassList.add(ConditionManagerFacade.class);
    sFacadeClassList.add(ContactsFacade.class);
    sFacadeClassList.add(EventFacade.class);
    sFacadeClassList.add(LocationFacade.class);
    sFacadeClassList.add(PhoneFacade.class);
    sFacadeClassList.add(PulseGeneratorFacade.class);
    sFacadeClassList.add(MediaRecorderFacade.class);
    sFacadeClassList.add(SensorManagerFacade.class);
    sFacadeClassList.add(SettingsFacade.class);
    sFacadeClassList.add(SmsFacade.class);
    sFacadeClassList.add(SpeechRecognitionFacade.class);
    sFacadeClassList.add(ToneGeneratorFacade.class);
    sFacadeClassList.add(WakeLockFacade.class);
    sFacadeClassList.add(WifiFacade.class);
    sFacadeClassList.add(UiFacade.class);

    if (sdkVersion >= 4) {
      sFacadeClassList.add(TextToSpeechFacade.class);
    } else {
      sFacadeClassList.add(EyesFreeFacade.class);
    }

    if (sdkVersion >= 5) {
      sFacadeClassList.add(BluetoothFacade.class);
    }

    if (sdkVersion >= 7) {
      sFacadeClassList.add(SignalStrengthFacade.class);
    }

    for (Class<? extends RpcReceiver> recieverClass : sFacadeClassList) {
      for (MethodDescriptor rpcMethod : MethodDescriptor.collectFrom(recieverClass)) {
        sRpcs.put(rpcMethod.getName(), rpcMethod);
      }
    }
  }

  private FacadeConfiguration() {
    // Utility class.
  }

  /** Returns a list of {@link MethodDescriptor} objects for all facades. */
  public static List<MethodDescriptor> collectRpcDescriptors() {
    return new ArrayList<MethodDescriptor>(sRpcs.values());
  }

  /** Returns a method by name. */
  public static MethodDescriptor getMethodDescriptor(String name) {
    return sRpcs.get(name);
  }

  public static Collection<Class<? extends RpcReceiver>> getFacadeClasses() {
    return sFacadeClassList;
  }
}