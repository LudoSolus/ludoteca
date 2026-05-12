<script lang="ts">
	import { generateId } from '$lib/shared/helpers/generate-id';

	export let id: string = generateId(5);
	export let label: string | null = null;
	export let checked: boolean = false;
	export let disabled: boolean = false;

	export let onChange: ((checked: boolean, id: string) => void) | undefined = undefined;

	function handleChange(event: Event) {
		const target = event.target as HTMLInputElement;
		checked = target.checked;
		if (onChange) {
			onChange(checked, id);
		}
	}
</script>

<div class="flex items-center gap-2">
	<input
		{id}
		type="checkbox"
		{disabled}
		bind:checked
		on:change={handleChange}
		class="h-4 w-4 cursor-pointer rounded border-gray-300 text-yellow-400 focus:ring-yellow-400 disabled:cursor-not-allowed"
	/>

	{#if label}
		<label
			for={id}
			class="cursor-pointer font-medium text-black select-none {disabled ? 'opacity-50' : ''}"
		>
			{label}
		</label>
	{/if}
</div>
