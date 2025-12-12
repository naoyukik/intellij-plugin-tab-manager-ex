package com.github.naoyukik.ijtabmanagerex.userInterface

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.vcs.log.VcsFullCommitDetails
import com.intellij.vcs.log.VcsLogDataKeys

private val LOG = Logger.getInstance(OpenFilesFromSelectedCommitsAction::class.java)

class OpenFilesFromSelectedCommitsAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        val selectedDetails = e.getData(VcsLogDataKeys.VCS_LOG_COMMIT_SELECTION)?.cachedFullDetails
        e.presentation.isEnabled = e.project != null && !selectedDetails.isNullOrEmpty()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: run {
            LOG.error("Project is null, cannot perform action.")
            return
        }

        // Get selected commits from the VCS log
        val selectedDetails = e.getData(VcsLogDataKeys.VCS_LOG_COMMIT_SELECTION)?.cachedFullDetails
        if (selectedDetails.isNullOrEmpty()) {
            LOG.info("No commits selected.")
            return
        }

        // Collect all changed file paths from the selected commits.
        val filePaths = getChangedFilePathsFromCommits(selectedDetails).toList()

        // Open each file in the editor
        val lastIndex = filePaths.lastIndex
        filePaths.forEachIndexed { index, filePath ->
            openFile(filePath, project, index == lastIndex)
        }
    }

    private fun getChangedFilePathsFromCommits(
        commits: List<VcsFullCommitDetails>,
    ): Set<String> {
        return commits.flatMap { commit ->
            commit.changes.mapNotNull { change ->
                change.afterRevision?.file?.path
            }
        }.toSet()
    }

    private fun openFile(filePath: String, project: Project, focus: Boolean = false) {
        val virtualFile = LocalFileSystem.getInstance().findFileByPath(filePath) ?: run {
            LOG.info("File not found: $filePath")
            return
        }

        FileEditorManager.getInstance(project).openFile(virtualFile, focus)
    }
}
