<script lang="ts">
	import type { IconDefinition } from '@fortawesome/free-brands-svg-icons';
	import Fa from 'svelte-fa';

	export let text: string;
	export let onClick: () => void;
	export let leftIcon: IconDefinition | null = null;
	export let rightIcon: IconDefinition | null = null;
</script>

<button
	class="flex items-center gap-2 rounded-md border-2 border-black px-6 py-1"
	on:click={onClick}
>
	{#if leftIcon}
		<Fa icon={leftIcon} />
	{/if}
	{text}
	{#if rightIcon}
		<Fa icon={rightIcon} />
	{/if}
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
