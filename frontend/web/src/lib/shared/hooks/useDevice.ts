import { browser } from '$app/environment';
import { writable } from 'svelte/store';

type DeviceType = 'mobile' | 'desktop';

function createDeviceStore() {
  const { subscribe, set } = writable<DeviceType>('desktop');

  if (browser) {
    function getDeviceType(): DeviceType {
      return window.innerWidth <= 768 ? 'mobile' : 'desktop';
    }

    function update() {
      set(getDeviceType());
    }

    update();
    window.addEventListener('resize', update);
  }

  return { subscribe };
}

export const device = createDeviceStore();
