<script lang="ts">
	export let onClickOuside: () => void;
</script>


<!-- svelte-ignore a11y_no_static_element_interactions -->
<!-- svelte-ignore a11y_click_events_have_key_events -->
<div
	class="absolute top-0 right-0 bottom-0 left-0 flex items-center justify-center backdrop-blur-md z-11"
	on:click={() => {
		onClickOuside();
	}}
>
	<slot />
</div>
