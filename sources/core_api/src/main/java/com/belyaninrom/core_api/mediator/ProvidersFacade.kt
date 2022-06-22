package com.belyaninrom.core_api.mediator

import com.belyaninrom.core_api.database.DatabaseProvider

interface ProvidersFacade : AppProvider, NetworkProvider, DatabaseProvider