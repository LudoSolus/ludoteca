<!-- BoardGameSkeleton.svelte -->
<script lang="ts">
	export let userType: 'admin' | 'user' = 'user';

	$: isAdmin = userType === 'admin';
	$: containerClasses = `flex items-center border-2 border-solid border-gray-200 rounded-r-[2rem] rounded-l-md min-h-12 min-[480px]:min-h-[10vw] min-[590px]:min-h-14 h-12 min-[480px]:h-[10vw] min-[590px]:h-14 relative ${
		isAdmin ? 'w-[94vw] min-[590px]:w-154' : 'w-[94vw] min-[590px]:w-136'
	}`;
	$: categoryClasses = `flex items-center w-56 max-[590px]:w-[32vw] max-[490px]:w-0 pl-16 max-[590px]:pl-16 max-[510px]:pl-[13vw] max-[430px]:pl-[16vw] max-[380px]:pl-[12vw] max-[350px]:pl-[8vw]`;
	$: endClasses = `justify-self-end flex items-between ${isAdmin ? 'w-38 max-[590px]:w-30' : ''}`;
</script>

<div class={containerClasses}>
	<div class="skeleton-pulse flex items-center justify-center w-16 h-full rounded-[3px] bg-gray-200">
		<div class="h-8 w-8 rounded bg-gray-300"></div>
	</div>

	<div class="grid items-center grid-cols-3 w-full">
		<div class="flex items-center justify-self-start w-60 max-[590px]:w-[34vw] max-[460px]:w-[36vw] max-[360px]:w-[32vw] pl-2">
			<div class="skeleton-pulse h-4 w-24 rounded bg-gray-300"></div>
		</div>

		<div class={categoryClasses}>
			<div class="skeleton-pulse flex items-center w-full">
				<div class="h-4 w-4 rounded bg-gray-300 shrink-0"></div>
				<div class="h-4 w-16 rounded bg-gray-300 ml-2"></div>
			</div>
		</div>

		<div class={endClasses}>
			<div class="flex items-center w-20 pl-2">
				<div class="skeleton-pulse h-4 w-4 rounded bg-gray-300 shrink-0"></div>
				<div class="skeleton-pulse h-4 w-8 rounded bg-gray-300 ml-2"></div>
			</div>
			{#if isAdmin}
				<div class="skeleton-pulse absolute top-1/2 -translate-y-1/2 right-3 h-10 w-10 rounded-full bg-gray-200"></div>
			{/if}
		</div>
	</div>
</div>

<style>
	.skeleton-pulse {
		animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
	}

	@keyframes pulse {
		0%, 100% {
			opacity: 1;
		}
		50% {
			opacity: 0.4;
		}
	}
</style>
