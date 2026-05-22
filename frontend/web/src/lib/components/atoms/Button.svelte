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
	export let variant: 'primary' | 'secondary' | 'black' = 'primary';
</script>

<button
	style="width: {width}; height: {height};"
	class="relative flex items-center justify-center gap-2 rounded-xl px-6 py-1 {variant}"
	disabled={disabled || loading}
	on:click={onClick}
>
	{#if loading}
		<Loading size="20px" color="#FFF" weight="3px" />
	{/if}

	{#if leftIcon}
		<Fa icon={leftIcon} class={loading ? 'invisible' : ''} />
	{/if}
	<span class={loading ? 'invisible' : ''}>{text}</span>
	{#if rightIcon}
		<Fa icon={rightIcon} class={loading ? 'invisible' : ''} />
	{/if}
</button>

<style>
	button {
		font-weight: 600;
		box-shadow: rgba(0, 0, 0, 0.5) 0px 4px 4px;
		cursor: pointer;
		transition: all 0.2s ease-in-out;
	}

	button.primary {
		background-color: var(--primary-color);
		border: black 2px solid;
	}

	button.secondary {
		background-color: white;
		border: black 2px solid;
	}

	button.black {
		background-color: #000;
		color: #fff;
	}

	button:hover {
		background-color: var(--hover-color);
		transition: all 0.2s ease-in-out;
	}

	button.black:hover {
		background-color: #232323;
	}

	button:active {
		background-color: var(--active-color);
		transition: all 0.2s ease-in-out;
	}

	button.black:active {
		color: #3f3f3f;
		box-shadow: none;
	}

	button:disabled {
		box-shadow: none;
		cursor: default;
		transition: all 0.2s ease-in-out;
	}

	button.primary:disabled {
		background-color: #beb05e;
	}

	button.secondary:disabled {
		background-color: #b2b2b2;
	}

	button.black:disabled {
		color: #eaeaea;
	}
</style>
