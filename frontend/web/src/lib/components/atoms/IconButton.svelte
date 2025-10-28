<script lang="ts">
	import type { IconDefinition } from '@fortawesome/free-brands-svg-icons';
	import Fa, { type IconSize } from 'svelte-fa';

	export let onClick: () => void;
	export let icon: IconDefinition;
	export let variant: 'primary' | 'naked' = 'primary';
	export let width: string = '100%';
	export let height: string = '100%';
	export let color: string = '#000';
	export let size: IconSize = '1x';
</script>

<button
	class="flex items-center justify-center rounded-md border-2 border-black {variant}"
	style="width: {width}; min-width: {width}; height: {height};"
	on:click={onClick}
>
	<Fa {icon} {color} {size} />
</button>

<style>
	button {
		font-weight: 600;
		cursor: pointer;
	}

	button.primary {
		background-color: var(--primary-color);
		box-shadow: rgba(0, 0, 0, 0.5) 0px 4px 4px;
	}

	button.naked {
		background-color: transparent;
		border: none;
		border-radius: 50px;
	}

	button:hover {
		background-color: var(--hover-color);
	}

	button:active {
		background-color: var(--active-color);
	}
</style>
