package org.objectweb.asm.commons;

import org.objectweb.asm.*;

public class AnnotationRemapper extends AnnotationVisitor
{
    protected final Remapper remapper;
    
    public AnnotationRemapper(final AnnotationVisitor annotationVisitor, final Remapper remapper) {
        this(458752, annotationVisitor, remapper);
    }
    
    protected AnnotationRemapper(final int api, final AnnotationVisitor annotationVisitor, final Remapper remapper) {
        super(api, annotationVisitor);
        this.remapper = remapper;
    }
    
    @Override
    public void visit(final String name, final Object value) {
        super.visit(name, this.remapper.mapValue(value));
    }
    
    @Override
    public void visitEnum(final String name, final String descriptor, final String value) {
        super.visitEnum(name, this.remapper.mapDesc(descriptor), value);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String name, final String descriptor) {
        final AnnotationVisitor annotationVisitor = super.visitAnnotation(name, this.remapper.mapDesc(descriptor));
        if (annotationVisitor == null) {
            return null;
        }
        return (annotationVisitor == this.av) ? this : new AnnotationRemapper(this.api, annotationVisitor, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitArray(final String name) {
        final AnnotationVisitor annotationVisitor = super.visitArray(name);
        if (annotationVisitor == null) {
            return null;
        }
        return (annotationVisitor == this.av) ? this : new AnnotationRemapper(this.api, annotationVisitor, this.remapper);
    }
}
