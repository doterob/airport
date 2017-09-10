package es.bahiasoftware.airport.business.service;

import static es.bahiasoftware.airport.model.test.AircraftUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.service.aircraft.AircraftImporter;
import es.bahiasoftware.airport.business.service.aircraft.AircraftServiceImpl;
import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.persistence.AircraftDao;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class AircraftServiceImplTest {

	@InjectMocks
	private AircraftServiceImpl service;
	@Mock
	private AircraftDao repository;
	@Mock
	private AircraftImporter importer;
	
	private final String idExists = "ML-388-A";
	private final String idNotExists = "X-XXXX-X";
	private final String idImported = "IM-001-Z";
	private final String manufacturerExists = "Airbus";
	private final String modelExists = "A320";
	private final String manufacturerImported = "Boeing";
	private final String modelImported = "777";
	private final Aircraft expected = aircraft(idExists, manufacturerExists, modelExists);
	private final Aircraft imported = aircraft(idImported, manufacturerImported, modelImported);

	@Before
	public void setUp() throws AppException {
		when(repository.findOne(idExists)).thenReturn(expected);
		when(repository.findByManufacturerAndModelAllIgnoreCase(manufacturerExists, modelExists)).thenReturn(expected);
		when(repository.save(expected)).thenReturn(expected);
		when(repository.save(imported)).thenReturn(imported);
		when(importer.find(manufacturerImported, modelImported)).thenReturn(imported);
	}

	@Test
	public void get_exist() throws AppException {
		final Aircraft actual = service.get(idExists);
		assertEquals(expected, actual);
	}

	@Test
	public void get_not_exist() throws AppException {
		final Aircraft actual = service.get(idNotExists);
		assertNull(actual);
	}

	@Test
	public void upsert_invoke_repository_save() throws AppException {
		service.upsert(expected);
		verify(repository, times(1)).save(expected);
	}

	@Test
	public void findAll_empty_list() throws AppException {
		final List<Aircraft> actual = service.findAll();
		verify(repository, times(1)).findAll();
		assertEquals(actual.size(), 0);
	}

	@Test
	public void findAll_not_empty_list() throws AppException {

		final List<Aircraft> expected = (List<Aircraft>) Arrays.asList(this.expected,
				aircraft(idNotExists));

		when(repository.findAll()).thenReturn(expected);
		final List<Aircraft> actual = service.findAll();
		verify(repository, times(1)).findAll();
		assertEquals(expected, actual);
	}
	
	@Test
	public void discover_exists() throws AppException {
		final Aircraft actual = service.discover(manufacturerExists, modelExists);
		assertEquals(expected, actual);
	}
	
	@Test
	public void discover_import() throws AppException {
		final Aircraft actual = service.discover(manufacturerImported, modelImported);
		assertEquals(imported, actual);
		verify(repository, times(1)).save(imported);
	}
	
	@Test
	public void discover_not_exists() throws AppException {
		final Aircraft actual = service.discover(manufacturerExists, modelImported);
		assertNull(actual);
	}
}
