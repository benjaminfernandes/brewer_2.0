package br.com.fernantech.brewer.repository.listener;

import javax.persistence.PostLoad;

import br.com.fernantech.brewer.BrewerApplication;
import br.com.fernantech.brewer.model.Cerveja;
import br.com.fernantech.brewer.storage.FotoStorage;
//Aula 32.11 
public class CervejaEntityListener {

	@SuppressWarnings("static-access")
	@PostLoad
	public void postLoad(final Cerveja cerveja) {

		FotoStorage fotoStorage = BrewerApplication.getBean(FotoStorage.class);
		cerveja.setUrlFoto(fotoStorage.getUrl(cerveja.getFotoOuMock()));
		cerveja.setUrlThumbnailFoto(fotoStorage.getUrl(fotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOuMock()));
	}
}
