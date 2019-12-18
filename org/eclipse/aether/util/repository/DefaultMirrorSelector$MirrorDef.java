package org.eclipse.aether.util.repository;

static class MirrorDef
{
    final String id;
    final String url;
    final String type;
    final boolean repositoryManager;
    final String mirrorOfIds;
    final String mirrorOfTypes;
    
    MirrorDef(final String id, final String url, final String type, final boolean repositoryManager, final String mirrorOfIds, final String mirrorOfTypes) {
        super();
        this.id = id;
        this.url = url;
        this.type = type;
        this.repositoryManager = repositoryManager;
        this.mirrorOfIds = mirrorOfIds;
        this.mirrorOfTypes = mirrorOfTypes;
    }
}
