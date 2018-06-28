package de.uniba.dsg.serverless.fibonacci.aws;

import java.applet.Applet;
import java.awt.color.ProfileDataException;
import java.awt.dnd.DragSource;
import java.awt.event.TextEvent;
import java.awt.geom.AffineTransform;
import java.awt.im.InputMethodHighlight;
import java.awt.image.renderable.ParameterBlock;
import java.awt.print.Book;
import java.beans.Beans;
import java.beans.beancontext.BeanContextChildSupport;
import java.io.File;
import java.io.IOException;
import java.math.MathContext;
import java.net.BindException;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.Attributes;
import java.util.logging.ConsoleHandler;
import java.util.prefs.BackingStoreException;
import java.util.zip.CRC32;

public class ClassDependingOnOthers {

	public static void main(String[] args) {
		
	}

	public void loadDependencies(long number) {
		if (number < 0) {
			new ArrayList<>();
			new ArrayBlockingQueue<>(1);
			new AtomicBoolean();
			new ReentrantLock();
			new ConsoleHandler();
			new Attributes();
			new BackingStoreException("");
			new CRC32();
			new Applet();
			new ProfileDataException("");
			new DragSource();
			new TextEvent(new Object(), 0);
			new AffineTransform();
			new InputMethodHighlight(false, 0);
			new ParameterBlock();
			new Book();
			new Beans();
			new BeanContextChildSupport();
			new File("");
			new BufferOverflowException();
			new MathContext(1);
			new BindException();
		}
	}

}
