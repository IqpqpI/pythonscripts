package main.groovy

/**
 * Created by DaKo on 12/14/2017.
 */
import org.sonatype.nexus.repository.storage.Query;
import org.sonatype.nexus.repository.storage.StorageFacet;
import groovy.json.JsonOutput;

def repositoryId = args.split(',')[0];
def groupId = args.split(',')[1];
def artifactId = args.split(',')[2];
def baseVersion = args.split(',')[3];
def latestOnly = args.split(',')[4];

def repo = repository.repositoryManager.get(repositoryId);
StorageFacet storageFacet = repo.facet(StorageFacet);
def tx = storageFacet.txSupplier().get();

tx.begin();
def components = tx.findComponents(Query.builder().where('group = ').param(groupId).and('name = ').param(artifactId).build(), [repo]);
def found = components.findAll{ it.attributes().child('maven2').get('baseVersion') == baseVersion}.collect {
    def version = it.attributes().child('maven2').get('version'); "${version}"
};

// found = found.unique().sort();
def latest = found.isEmpty() ? found : found.last();

tx.commit();
def result = latestOnly == 'latest' ? JsonOutput.toJson(latest) : JsonOutput.toJson(found);

return result;