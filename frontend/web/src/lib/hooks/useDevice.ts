import { browser } from '$app/environment';
import { writable } from 'svelte/store';

function createDeviceStore() {
  const { subscribe, set } = writable<'mobile' | 'desktop'>('desktop');

  if (browser) {
    function getDeviceType(): 'mobile' | 'desktop' {
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
