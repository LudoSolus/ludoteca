<script lang="ts">
	import type { IconDefinition } from '@fortawesome/free-brands-svg-icons';
	import Fa, { type IconSize } from 'svelte-fa';

	export let onClick: () => void;
	export let icon: IconDefinition;
	export let width: string = '100%';
	export let height: string = '100%';
	export let color: string = '#000';
	export let size: IconSize = '1x'
</script>

<button
	class="flex items-center justify-center rounded-md border-2 border-black"
	style="width: {width}; min-width: {width}; height: {height};"
	on:click={onClick}
>
	<Fa {icon} color={color} size={size} />
</button>

<style>
	button {
		background-color: var(--primary-color);
		font-weight: 600;
		box-shadow: rgba(0, 0, 0, 0.5) 0px 4px 4px;
		cursor: pointer;
	}

	button:hover {
		background-color: var(--hover-color);
	}

	button:active {
		background-color: var(--active-color);
	}
</style>
