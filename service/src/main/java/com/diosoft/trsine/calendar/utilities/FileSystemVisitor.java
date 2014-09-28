package com.diosoft.trsine.calendar.utilities;

import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by VARTAN on 28.09.2014.
 */
public class FileSystemVisitor extends SimpleFileVisitor<Path>{

    private PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.{xml}");

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        super.visitFile(file, attrs);
        boolean res = file.toString().endsWith(".xml");
        if (!res){
            return FileVisitResult.TERMINATE;
        }

        try {
            ConvertData.createEventOfFile(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IncorrectPeriodDates incorrectPeriodDates) {
            incorrectPeriodDates.printStackTrace();
        }

        return FileVisitResult.CONTINUE;
    }

}
