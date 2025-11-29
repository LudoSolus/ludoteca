<script lang="ts">
	let { children } = $props();
</script>

<main class="flex h-full w-full items-center justify-center">
	<section
		class="container flex w-full items-center justify-center px-3 py-5 sm:max-w-150 sm:rounded-2xl sm:p-5"
	>
		{@render children?.()}
	</section>
</main>

<style>
	.container {
		background: var(--card-background-color);
		box-shadow: 0 4px 4px rgba(0, 0, 0, 0.7);
	}
</style>
