import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-local-gen-ai' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const LocalGenaiModule = isTurboModuleEnabled
  ? require('./NativeLocalGenai').default
  : NativeModules.LocalGenai;

const LocalGenai = LocalGenaiModule
  ? LocalGenaiModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function chatWithLLM(prompt: string): Promise<string> {
  return LocalGenai.chatWithLLM(prompt);
}

export function setModelPath(path: string): Promise<string> {
  return LocalGenai.setModelPath(path);
}

export function setModelOptions(options: {
  modelPath?: string;
  maxTokens?: number;
  topK?: number;
  temperature?: number;
  randomSeed?: number;
}): Promise<string> {
  return LocalGenai.setModelOptions(options);
}
