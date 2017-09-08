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
import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.persistence.AircraftDao;

@RunWith(MockitoJUnitRunner.class)
public class AircraftServiceImplTest {

	@InjectMocks
	private AircraftServiceImpl service;

	@Mock
	private AircraftDao repository;
	
	private final String idExists = "388";
	private final String idNotExists = "XXXX";
	private final Aircraft expected = aircraft(idExists);

	@Before
	public void setUp() throws AppException {
		when(repository.findOne(idExists)).thenReturn(expected);
	}

	@Test
	public void get_exist() throws AppException {
		final Aircraft aircraft = service.get(idExists);
		assertEquals(expected, aircraft);
	}

	@Test(expected = AppException.class)
	public void get_not_exist() throws AppException {
		service.get(idNotExists);
	}

	@Test
	public void upsert_invoke_repository_save() throws AppException {
		service.upsert(expected);
		verify(repository, times(1)).save(expected);
	}

	@Test
	public void findAll_empty_list() throws AppException {
		final List<Aircraft> current = service.find();
		verify(repository, times(1)).findAll();
		assertEquals(current.size(), 0);
	}

	@Test
	public void findAll_not_empty_list() throws AppException {

		final List<Aircraft> expected = (List<Aircraft>) Arrays.asList(this.expected,
				aircraft(idNotExists));

		when(repository.findAll()).thenReturn(expected);
		final List<Aircraft> current = service.find();
		verify(repository, times(1)).findAll();
		assertEquals(expected, current);
	}
}
