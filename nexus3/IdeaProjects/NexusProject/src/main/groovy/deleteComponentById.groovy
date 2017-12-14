package main.groovy

/**
 * Created by DaKo on 12/14/2017.
 */
import org.sonatype.nexus.repository.storage.Query;
import org.sonatype.nexus.repository.storage.StorageFacet;
import groovy.json.JsonOutput
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.storage.Bucket;

def repo = repository.repositoryManager.get('dako0516DockerReg');

StorageFacet storageFacet = repo.facet(StorageFacet);
StorageTx tx = storageFacet.txSupplier().get();
Bucket bucket = tx.findBucket(repository);
tx.browseComponents(bucket).find {}
def name = 'my_image';
def version = 'build3';

tx.begin();
def components = tx.findComponents(Query.builder().where('name = ').param(name).and('version = ').param(version).build(), [repo]);
def found = components.findAll();
tx.commit();


return JsonOutput.toJson(components);