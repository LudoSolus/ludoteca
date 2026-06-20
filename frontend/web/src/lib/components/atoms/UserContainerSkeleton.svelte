<!-- UserContainerSkeleton.svelte -->
<div class="user-container-skeleton flex items-center justify-between min-h-12 min-[590px]:min-h-12 h-12 min-[590px]:h-12 w-full gap-2">
	<div class="skeleton-pulse type-icon-box flex items-center justify-center justify-self-start bg-gray-200">
		<div class="h-6 w-6 rounded bg-gray-300"></div>
	</div>

	<div class="grid grid-cols-[1fr_auto] items-center w-full pl-2 pr-4 gap-2">
		<div class="min-w-0 text-start">
			<div class="skeleton-pulse h-4 w-40 rounded bg-gray-300"></div>
		</div>

		<div class="flex gap-2 items-center justify-self-end w-26 px-2 text-start whitespace-nowrap">
			<div class="skeleton-pulse h-4 w-16 rounded bg-gray-300"></div>
		</div>
	</div>
</div>

<style>
	.user-container-skeleton {
		background-color: white;
		border: 2px solid #e5e7eb; /* border-gray-200 */
		border-top-left-radius: 10px;
		border-bottom-left-radius: 10px;
		border-top-right-radius: 50px;
		border-bottom-right-radius: 50px;
	}

	.type-icon-box {
		width: 60px;
		min-width: 60px;
		height: 100%;
		border-top-left-radius: 8px;
		border-bottom-left-radius: 8px;
	}

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
