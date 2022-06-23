package ru.sorter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import static ru.sorter.util.FilesUtil.deleteFilesByName;
import static ru.sorter.util.FilesUtil.mergeFiles;
/**Recursively merges the given list of sorted files, located in a given directory into one sorted file*/
public class MergeFilesTask extends RecursiveTask<String> {

    public MergeFilesTask(String directory, List<String> filenames) {
        this.directory = directory;
        this.filenames = filenames;
    }

    private final String directory;
    private final List<String> filenames;

    @Override
    protected String compute() {
        int size = filenames.size();
        //Task splits into smaller peaces
        if (size > 2) {
            List<String> leftNames = filenames.subList(0, size >>> 1);
            List<String> rightNames = filenames.subList(size >>> 1, size);
            try {
                MergeFilesTask leftTask = new MergeFilesTask(directory, leftNames);
                MergeFilesTask rightTask = new MergeFilesTask(directory, rightNames);
                invokeAll(leftTask, rightTask); //blocking until done
                String leftName = leftTask.get();
                String rightName = rightTask.get();
                String fileName = mergeFiles(directory, leftName, rightName);
                deleteFilesByName(Paths.get(directory), leftName, rightName);
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Task performs calculations itself
        if (size == 2) {
            try {
                String leftName = filenames.get(0);
                String rightName = filenames.get(1);
                String fileName = mergeFiles(directory, leftName, rightName);
                deleteFilesByName(Paths.get(directory), leftName, rightName);
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filenames.get(0);
    }
}
