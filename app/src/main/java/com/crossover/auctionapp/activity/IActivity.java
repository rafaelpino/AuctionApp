package com.crossover.auctionapp.activity;

/**
 * Created by rafaelpino on 12/13/16.
 */
public interface IActivity {
    /**
     * Method to show a progress bar if necesary
     * @param show
     */
    public void showProgress(final boolean show);

    /**
     * Clear all pending tasks in activity
     */
    public void clearTasks();

    /**
     * Callback method for agent execution
     */
    public void onAgentExecution();
}
