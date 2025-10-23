<script lang="ts">
	import type { IconDefinition } from '@fortawesome/free-brands-svg-icons';
	import Fa from 'svelte-fa';
	import Loading from './Loading.svelte';

	export let text: string;
	export let onClick: () => void;
	export let leftIcon: IconDefinition | null = null;
	export let rightIcon: IconDefinition | null = null;
	export let disabled: boolean = false;
	export let loading: boolean = false;
	export let width: string = 'fit-content';
	export let height: string = 'fit-content';
</script>

<button
	style="width: {width}; height: {height};"
	class="flex items-center justify-center gap-2 rounded-md border-2 border-black px-6 py-1"
	disabled={disabled || loading}
	on:click={onClick}
>
	{#if loading}
		<Loading size="20px" color="#FFF" weight="3px"/>
	{:else}
		{#if leftIcon}
			<Fa icon={leftIcon} />
		{/if}
		{text}
		{#if rightIcon}
			<Fa icon={rightIcon} />
		{/if}
	{/if}
</button>

<style>
	button {
		background-color: var(--primary-color);
		font-weight: 600;
		box-shadow: rgba(0, 0, 0, 0.5) 0px 4px 4px;
		cursor: pointer;
		transition: all 0.2s ease-in-out;
	}

	button:hover {
		background-color: var(--hover-color);
		transition: all 0.2s ease-in-out;
	}

	button:active {
		background-color: var(--active-color);
		transition: all 0.2s ease-in-out;
	}

	button:disabled {
		background-color: #beb05e;
		box-shadow: none;
		cursor: default;
		transition: all 0.2s ease-in-out;
	}
</style>
