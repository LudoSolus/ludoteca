<script lang="ts">
	import { goto } from '$app/navigation';
	import type { IconDefinition } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';

	export let name: string;
	export let path: string;
	export let selected: boolean;
	export let onClick: () => void = () => {};
	export let icon: IconDefinition | null = null;

	function handleClick() {
		onClick();
		goto(path);
	}
</script>

<button on:click={() => handleClick()} class="route-button {selected ? 'selected' : ''}">
	{#if icon}
		<div class="icon-wrapper">
			<Fa {icon} size="lg" />
		</div>
	{/if}
	<span class="route-name">{name}</span>
</button>

<style>
	.route-button {
		cursor: pointer;
        font-weight: 500;
        transition: color 0.3s ease-in;
	}

    .selected {
		text-shadow: 0.5px 0.5px 1px #000000;
    }
</style>
