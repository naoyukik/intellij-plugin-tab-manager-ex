package com.github.naoyukik.ijtabmanagerex.userInterface

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
        val filePaths = getChangedFilePathsFromCommits(selectedDetails)

        // Open each file in the editor
        filePaths.forEach { filePath ->
            openFile(filePath, project)
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

    private fun openFile(filepath: String, project: Project) {
        val virtualFile = LocalFileSystem.getInstance().findFileByPath(filepath) ?: run {
            LOG.info("File not found: $filepath")
            return
        }

        FileEditorManager.getInstance(project).openFile(virtualFile, true)
    }
}
