package es.bahiasoftware.airport.business.manager;

import static es.bahiasoftware.airport.model.test.AircraftUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.service.aircraft.AircraftService;
import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.model.AircraftStatus;

@RunWith(MockitoJUnitRunner.class)
public class FleetManagerImplTest {

	@InjectMocks
	private FleetManagerImpl manager;

	@Mock
	private AircraftService service;

	private final String idExists = "388";
	private final String idRetired = "RRRR";
	private final Aircraft expected = aircraft(idExists);
	private final Aircraft retired = aircraft(idRetired, AircraftStatus.RETIRED);

	@Before
	public void setUp() throws AppException {
		when(service.get(idExists)).thenReturn(expected);
		when(service.get(idRetired)).thenReturn(retired);
	}

	@Test
	public void retire() throws AppException {
		final Aircraft expected = aircraft(idExists, AircraftStatus.RETIRED);
		final Aircraft current = manager.retire(idExists);
		verify(service, times(1)).get(idExists);
		verify(service, times(1)).upsert(expected);
		assertEquals(expected, current);
	}

	@Test(expected = AppException.class)
	public void retire_already_retired() throws AppException {
		manager.retire(idRetired);
	}
}
