import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  chatWithLLM(prompt: string): Promise<string>;
  setModelPath(path: string): Promise<string>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('LocalGenai');
