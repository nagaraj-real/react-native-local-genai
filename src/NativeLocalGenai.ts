import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  chatWithLLM(prompt: string): Promise<string>;
  setModelPath(path: string): Promise<string>;
  setModelOptions(options: {
    modelPath?: string;
    maxTokens?: number;
    topK?: number;
    temperature?: number;
    randomSeed?: number;
  }): Promise<string>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('LocalGenai');
