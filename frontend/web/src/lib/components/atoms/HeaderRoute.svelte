<script lang="ts">
	import { goto } from '$app/navigation';

	export let name: string;
	export let path: string;
	export let selected: boolean;
</script>

<button on:click={() => goto(path)} class="{selected ? 'selected' : ''}">
	{name}
</button>

<style>
	button {
		cursor: pointer;
        font-weight: 500;
        transition: color 0.3s ease-in;
	}

    .selected {
		text-shadow: 0.5px 0.5px 1px #000000;
    }
</style>
