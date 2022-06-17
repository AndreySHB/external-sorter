package main;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import static main.Util.merge2FilesAndDeletePrevious;

public class MyTask extends RecursiveTask<String> {

    public MyTask(List<String> filenames) {
        this.filenames = filenames;
    }
    private final List<String> filenames;

    @Override
    protected String compute() {
        int size = filenames.size();
        if (size > 2) {
            List<String> leftNames = filenames.subList(0, size >>> 1);
            List<String> rightNames = filenames.subList(size >>> 1, size);
            try {
                String leftName = new MyTask(leftNames).invoke();
                String rightName = new MyTask(rightNames).invoke();
                return merge2FilesAndDeletePrevious(leftName, rightName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (size == 2) {
            try {
                return merge2FilesAndDeletePrevious(filenames.get(0), filenames.get(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filenames.get(0);
    }
}
